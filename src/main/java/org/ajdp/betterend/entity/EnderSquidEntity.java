package org.ajdp.betterend.entity;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnderSquidEntity extends CreatureEntity {
	public float squidPitch;
	public float prevSquidPitch;
	public float squidYaw;
	public float prevSquidYaw;
	public float squidRotation;
	public float prevSquidRotation;
	public float tentacleAngle;
	public float lastTentacleAngle;
	private float randomMotionSpeed;
	private float rotationVelocity;
	private float rotateSpeed;
	private float randomMotionVecX;
	private float randomMotionVecY;
	private float randomMotionVecZ;

	public EnderSquidEntity(EntityType<? extends EnderSquidEntity> type, World worldIn) {
		super(type, worldIn);
		setNoGravity(true);
		this.rand.setSeed((long) this.getEntityId());
		this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new EnderSquidEntity.MoveRandomGoal(this));
	}

	public static AttributeModifierMap getAttributeModifierMap() {
		return CreatureEntity.func_233666_p_().func_233815_a_(Attributes.field_233818_a_, 10.0D).func_233813_a_();
	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return sizeIn.height * 0.5F;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_SQUID_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_SQUID_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_SQUID_DEATH;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume() {
		return 0.4F;
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	public void livingTick() {
		super.livingTick();
		this.prevSquidPitch = this.squidPitch;
		this.prevSquidYaw = this.squidYaw;
		this.prevSquidRotation = this.squidRotation;
		this.lastTentacleAngle = this.tentacleAngle;
		this.squidRotation += this.rotationVelocity;
		if ((double) this.squidRotation > (Math.PI * 2D)) {
			if (this.world.isRemote) {
				this.squidRotation = ((float) Math.PI * 2F);
			} else {
				this.squidRotation = (float) ((double) this.squidRotation - (Math.PI * 2D));
				if (this.rand.nextInt(10) == 0) {
					this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
				}

				this.world.setEntityState(this, (byte) 19);
			}
		}

		if (this.squidRotation < (float) Math.PI) {
			float f = this.squidRotation / (float) Math.PI;
			this.tentacleAngle = MathHelper.sin(f * f * (float) Math.PI) * (float) Math.PI * 0.25F;
			if ((double) f > 0.75D) {
				this.randomMotionSpeed = 1.0F;
				this.rotateSpeed = 1.0F;
			} else {
				this.rotateSpeed *= 0.8F;
			}
		} else {
			this.tentacleAngle = 0.0F;
			this.randomMotionSpeed *= 0.9F;
			this.rotateSpeed *= 0.99F;
		}

		if (!this.world.isRemote) {
			this.setMotion((double) (this.randomMotionVecX * this.randomMotionSpeed),
					(double) (this.randomMotionVecY * this.randomMotionSpeed),
					(double) (this.randomMotionVecZ * this.randomMotionSpeed));
		}

		Vector3d vector3d = this.getMotion();
		float f1 = MathHelper.sqrt(horizontalMag(vector3d));
		this.renderYawOffset += (-((float) MathHelper.atan2(vector3d.x, vector3d.z)) * (180F / (float) Math.PI)
				- this.renderYawOffset) * 0.1F;
		this.rotationYaw = this.renderYawOffset;
		this.squidYaw = (float) ((double) this.squidYaw + Math.PI * (double) this.rotateSpeed * 1.5D);
		this.squidPitch += (-((float) MathHelper.atan2((double) f1, vector3d.y)) * (180F / (float) Math.PI)
				- this.squidPitch) * 0.1F;

		setNoGravity(!isTooHigh());
	}

	private boolean isTooHigh() {
		BlockPos pos = new BlockPos(getPositionVec());
		for (int i = 0; i < 10; i++) {
			BlockPos newPos = pos.offset(Direction.DOWN, i);
			if (!world.getBlockState(newPos).isAir(world, newPos))
				return false;
		}
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (super.attackEntityFrom(source, amount) && this.getRevengeTarget() != null) {
			this.squirtInk();
			return true;
		} else {
			return false;
		}
	}

	private Vector3d func_207400_b(Vector3d p_207400_1_) {
		Vector3d vector3d = p_207400_1_.rotatePitch(this.prevSquidPitch * ((float) Math.PI / 180F));
		return vector3d.rotateYaw(-this.prevRenderYawOffset * ((float) Math.PI / 180F));
	}

	private void squirtInk() {
		this.playSound(SoundEvents.ENTITY_SQUID_SQUIRT, this.getSoundVolume(), this.getSoundPitch());
		Vector3d vector3d = this.func_207400_b(new Vector3d(0.0D, -1.0D, 0.0D)).add(this.getPosX(), this.getPosY(),
				this.getPosZ());

		for (int i = 0; i < 30; ++i) {
			Vector3d vector3d1 = this.func_207400_b(new Vector3d((double) this.rand.nextFloat() * 0.6D - 0.3D, -1.0D,
					(double) this.rand.nextFloat() * 0.6D - 0.3D));
			Vector3d vector3d2 = vector3d1.scale(0.3D + (double) (this.rand.nextFloat() * 2.0F));
			((ServerWorld) this.world).spawnParticle(ParticleTypes.SQUID_INK, vector3d.x, vector3d.y + 0.5D, vector3d.z,
					0, vector3d2.x, vector3d2.y, vector3d2.z, (double) 0.1F);
		}

	}

	public void travel(Vector3d p_213352_1_) {
		this.move(MoverType.SELF, this.getMotion());
	}

	/**
	 * Handler for {@link World#setEntityState}
	 */
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 19) {
			this.squidRotation = 0.0F;
		} else {
			super.handleStatusUpdate(id);
		}

	}

	public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
		this.randomMotionVecX = randomMotionVecXIn;
		this.randomMotionVecY = randomMotionVecYIn;
		this.randomMotionVecZ = randomMotionVecZIn;
	}

	public boolean hasMovementVector() {
		return this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F;
	}

	class MoveRandomGoal extends Goal {
		private final EnderSquidEntity squid;

		public MoveRandomGoal(EnderSquidEntity p_i48823_2_) {
			this.squid = p_i48823_2_;
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state
		 * necessary for execution in this method as well.
		 */
		public boolean shouldExecute() {
			return true;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			int i = this.squid.getIdleTime();
			if (i > 100) {
				this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
			} else if (this.squid.getRNG().nextInt(50) == 0 || !this.squid.hasMovementVector()) {
				float f = this.squid.getRNG().nextFloat() * ((float) Math.PI * 2F);
				float f1 = MathHelper.cos(f) * 0.2F;
				float f2 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
				float f3 = MathHelper.sin(f) * 0.2F;
				this.squid.setMovementVector(f1, f2, f3);
			}

		}
	}
}