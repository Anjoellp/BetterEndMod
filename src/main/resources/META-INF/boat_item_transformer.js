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
		"getItemBoat" : {
			"target" : {
				"type" : "CLASS",
				"name" : "net.minecraft.entity.item.BoatEntity"
			},
			"transformer" : function(classNode) {
				var methodIndex = findMethod(classNode, {
					"name" : "getItemBoat",
					"oName" : "func_184455_j",
					"desc" : "()Lnet/minecraft/item/Item;"
				});
				if (methodIndex == -1)
					return classNode;
				var method = classNode.methods[methodIndex];
				method.instructions.clear();
				method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
				method.instructions
						.add(new MethodInsnNode(
								Opcodes.INVOKESTATIC,
								"org/ajdp/betterend/entity/ModBoatTypes",
								"getBoatItem",
								"(Lnet/minecraft/entity/item/BoatEntity;)Lnet/minecraft/item/Item;",
								false));
				method.instructions.add(new InsnNode(Opcodes.ARETURN));
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