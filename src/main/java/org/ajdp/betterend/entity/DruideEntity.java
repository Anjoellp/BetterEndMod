package org.ajdp.betterend.entity;

import org.ajdp.betterend.sounds.ModSoundEvents;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;

public class DruideEntity extends MonsterEntity implements IRangedAttackMob {
	public static enum AttackMode implements IStringSerializable {
		ENDERMITE_SPAWNING("endermite_spawning"), POTION_THROWING("potion_throwing");

		private String name;

		private AttackMode(String name) {
			this.name = name;
		}

		@Override
		public String func_176610_l() {
			return name;
		}

		public static AttackMode getByName(String string) {
			for (AttackMode mode : values()) {
				if (mode.func_176610_l().equals(string))
					return mode;
			}
			return null;
		}
	}

	public static final DataParameter<String> ATTACK_MODE = EntityDataManager.createKey(DruideEntity.class,
			DataSerializers.STRING);
	public static final DataParameter<Integer> ATTACKS_AFTER_MODE_SWITCHING = EntityDataManager
			.createKey(DruideEntity.class, DataSerializers.VARINT);
	private final ServerBossInfo bossInfo = new ServerBossInfo(getDisplayName(), Color.PURPLE, Overlay.PROGRESS);

	protected DruideEntity(EntityType<DruideEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(ATTACK_MODE, AttackMode.POTION_THROWING.func_176610_l());
		this.dataManager.register(ATTACKS_AFTER_MODE_SWITCHING, 0);
	}

	@Override
	protected Builder getLootContextBuilder(boolean p_213363_1_, DamageSource damageSourceIn) {
		Builder ret = super.getLootContextBuilder(p_213363_1_, damageSourceIn);
		if (damageSourceIn.getTrueSource() instanceof LivingEntity && damageSourceIn instanceof EntityDamageSource
				&& !((EntityDamageSource) damageSourceIn).getIsThornsDamage()) {
			LivingEntity entity = (LivingEntity) damageSourceIn.getTrueSource();
			if (damageSourceIn.damageType.equals("player") || damageSourceIn.damageType.equals("mob"))
				ret.withParameter(LootParameters.TOOL, entity.getHeldItemMainhand());
		}
		return ret;
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putString("AttackMode", dataManager.get(ATTACK_MODE));
		compound.putInt("AttacksAfterModeSwitching", dataManager.get(ATTACKS_AFTER_MODE_SWITCHING));
	}

	@Override
	public void setCustomName(ITextComponent name) {
		super.setCustomName(name);
		bossInfo.setName(name);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("CustomName")) {
			bossInfo.setName(compound.get("CustomName").toFormattedComponent());
		}
		dataManager.set(ATTACK_MODE, compound.getString("AttackMode"));
		dataManager.set(ATTACKS_AFTER_MODE_SWITCHING, compound.getInt("AttacksAfterModeSwitching"));
	}

	public AttackMode getAttackMode() {
		return AttackMode.getByName(dataManager.get(ATTACK_MODE));
	}

	public void setAttackMode(AttackMode mode) {
		dataManager.set(ATTACK_MODE, mode.func_176610_l());
		dataManager.set(ATTACKS_AFTER_MODE_SWITCHING, 0);
	}

	@Override
	public void addTrackingPlayer(ServerPlayerEntity player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(ServerPlayerEntity player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		bossInfo.setPercent(getHealth() / getMaxHealth());
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 20, 10.0F));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2,
				new HurtByTargetGoal(this, PlayerEntity.class, DruideEntity.class, EndermiteEntity.class));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, WanderingTraderEntity.class, true));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ENTITY_DRUIDE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSoundEvents.ENTITY_DRUIDE_DAMAGE;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENTITY_DRUIDE_DEATH;
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 1.62F;
	}

	public static AttributeModifierMap.MutableAttribute getAttributeModifierMap() {
		return MonsterEntity.func_234295_eP_().func_233815_a_(Attributes.field_233818_a_, 120.0D)
				.func_233815_a_(Attributes.field_233821_d_, 0.25D).func_233815_a_(Attributes.field_233824_g_, 4)
				.func_233815_a_(Attributes.field_233823_f_, 8);
	}

	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
		super.damageEntity(damageSrc, damageAmount);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		if (source.isMagicDamage() || source.isFireDamage())
			return true;
		if (source.getTrueSource() != null && source.getTrueSource().getType() == EntityType.ENDERMITE)
			return true;
		return super.isInvulnerableTo(source);
	}

	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		if (getAttackMode() == null || getAttackMode() == AttackMode.POTION_THROWING) {
			setAttackMode(AttackMode.POTION_THROWING);
			if (rand.nextInt(20) == 0)
				setAttackMode(AttackMode.ENDERMITE_SPAWNING);
			Vector3d vector3d = target.getMotion();
			double d0 = target.getPosX() + vector3d.x - this.getPosX();
			double d1 = target.getPosYEye() - (double) 1.1F - this.getPosY();
			double d2 = target.getPosZ() + vector3d.z - this.getPosZ();
			float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
			Potion potion = Potions.HARMING;
			if (f >= 8.0F && !target.isPotionActive(Effects.SLOWNESS)) {
				potion = Potions.SLOWNESS;
			} else if (target.getHealth() >= 8.0F && !target.isPotionActive(Effects.POISON)) {
				potion = Potions.POISON;
			} else if (f <= 3.0F && !target.isPotionActive(Effects.WEAKNESS) && this.rand.nextFloat() < 0.25F) {
				potion = Potions.WEAKNESS;
			}

			PotionEntity potionentity = new PotionEntity(this.world, this);
			potionentity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potion));
			potionentity.rotationPitch -= -20.0F;
			potionentity.shoot(d0, d1 + (double) (f * 0.2F), d2, 0.75F, 8.0F);
			if (!this.isSilent()) {
				this.world.playSound((PlayerEntity) null, this.getPosX(), this.getPosY(), this.getPosZ(),
						SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F,
						0.8F + this.rand.nextFloat() * 0.4F);
			}

			this.world.addEntity(potionentity);
		} else if (getAttackMode() == AttackMode.ENDERMITE_SPAWNING) {
			int attacks = dataManager.get(ATTACKS_AFTER_MODE_SWITCHING);
			int endermites = 10;
			if (attacks >= endermites)
				setAttackMode(AttackMode.POTION_THROWING);
			Vector3d pos = target.getPositionVec();
			Vector3d spawnPos = null;
			for (int i = 0; i < 20; i++) {
				double xoffset = (rand.nextDouble() * 3) + 1;
				double zoffset = (rand.nextDouble() * 3) + 1;
				Vector3d sPos = pos.add(xoffset, 0, zoffset);
				BlockPos bpos = new BlockPos(sPos);
				if (world.getBlockState(bpos).isAir(world, bpos)) {
					spawnPos = sPos;
					break;
				}
			}
			if (spawnPos == null) {
				return;
			}
			BlockPos bspos = new BlockPos(spawnPos);
			EndermiteEntity mite = EntityType.ENDERMITE.create(world);
			mite.setPosition(bspos.getX(), bspos.getY(), bspos.getZ());
			mite.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(spawnPos)), SpawnReason.TRIGGERED,
					null, null);
			world.addEntity(mite);
			dataManager.set(ATTACKS_AFTER_MODE_SWITCHING, ++attacks);
		}
	}

	@Override
	public boolean isBurning() {
		return false;
	}

	@Override
	public boolean hitByEntity(Entity entityIn) {
		if (entityIn instanceof LivingEntity
				&& (getAttackMode() == AttackMode.ENDERMITE_SPAWNING || rand.nextInt(3) == 0)) {
			LivingEntity entity = (LivingEntity) entityIn;
			if (getAttackTarget() != entity) {
				setAttackTarget(entity);
			}
			if (entity instanceof EndermiteEntity) {
				entity.onDeath(DamageSource.MAGIC);
				return true;
			}
			attackEntityAsMob(entity);
			switchAttackMode();
		}
		return super.hitByEntity(entityIn);
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		world.playSound(getPosX(), getPosY() + 1, getPosZ(), ModSoundEvents.ENTITY_DRUIDE_ATTACK, SoundCategory.HOSTILE,
				getSoundVolume(), getSoundPitch(), false);
		return super.attackEntityAsMob(entityIn);
	}

	public void switchAttackMode() {
		setAttackMode(getAttackMode() == AttackMode.POTION_THROWING ? AttackMode.ENDERMITE_SPAWNING
				: AttackMode.POTION_THROWING);
	}

}
