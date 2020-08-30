var Opcodes = Java.type("org.objectweb.asm.Opcodes");
var InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
var InsnList = Java.type("org.objectweb.asm.tree.InsnList");
var VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
var LabelNode = Java.type("org.objectweb.asm.tree.LabelNode");
var JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode");
var MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
var TypeInsnNode = Java.type("org.objectweb.asm.tree.TypeInsnNode");
var ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
function initializeCoreMod() {
	return {
		"getFireState" : {
			"target" : {
				"type" : "CLASS",
				"name" : "net.minecraft.world.DimensionType"
			},
			"transformer" : function(classNode) {
				var methodIndex = findMethod(classNode, {
					"name" : "func_236038_d_",
					"oName" : "func_236038_d_",
					"desc" : "(J)Lnet/minecraft/world/gen/ChunkGenerator;"
				});
				if (methodIndex == -1)
					return classNode;
				var method = classNode.methods[methodIndex];
				var j = 0;
				var k = false;
				for (var i = 0; i < method.instructions.size(); i++) {
					var insn = method.instructions.get(i);
					if (insn.getOpcode() == Opcodes.NEW) {
						if (j == 1) {
							method.instructions
									.set(
											insn,
											new TypeInsnNode(Opcodes.NEW,
													"org/ajdp/betterend/biomes/CustomEndBiomeProvider"));
						}
						j++;
					}
					if (insn.getOpcode() == Opcodes.INVOKESPECIAL && !k) {
						method.instructions
								.set(
										insn,
										new MethodInsnNode(
												Opcodes.INVOKESPECIAL,
												"org/ajdp/betterend/biomes/CustomEndBiomeProvider",
												"<init>", "(J)V", false));
						k = true;
					}
				}
				return classNode;
			}
		}
	}
}

function findMethod(classNode, entry) {
	for (var i = 0; i < classNode.methods.length; i++) {
		var method = classNode.methods[i];
		if ((method.name.equals(entry.name) || method.name.equals(entry.oName))
				&& method.desc.equals(entry.desc)) {
			return i;
		}
	}
	return -1;
}