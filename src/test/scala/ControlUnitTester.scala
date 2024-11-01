import chisel3._
import chisel3.iotesters.PeekPokeTester

class ControlUnitTester(dut: ControlUnit) extends PeekPokeTester(dut) {
  // Test Load Data (LD)
  poke(dut.io.instr, "b010000_00_00000000_00000000_00000000".U)
  val immEnable = peek(dut.io.immEnable) == 1
  val memRead = peek(dut.io.memRead) == 1
  val memWrite = peek(dut.io.memWrite) == 1
  val regWrite = peek(dut.io.regWrite) == 1

  val aluOrMem = peek(dut.io.aluOrMem) == 1
  val writeImmEnable = peek(dut.io.writeImmEnable) == 1
  val jump = peek(dut.io.jump) == 1
  val jumpEq = peek(dut.io.jumpEq) == 1

  expect(!immEnable, "Expected immEnable to be false, got " + immEnable)
  expect(memRead, "Expected memRead to be true, got " + memRead)
  expect(!memWrite, "Expected memWrite to be false, got " + memWrite)
  expect(regWrite, "Expected regWrite to be true, got " + regWrite)

  expect(!aluOrMem, "Expected aluOrMem to be false, got " + aluOrMem)
  expect(!writeImmEnable, "Expected writeImmEnable to be false, got " + writeImmEnable)
  expect(!jump, "Expected jump to be false, got " + jump)
  expect(!jumpEq, "Expected jumpEq to be false, got " + jumpEq)
  step(5)

  // Test Load Immediate (LI)
  poke(dut.io.instr, "b011000_00_00000000_00000000_00000000".U)
  val immEnable2 = peek(dut.io.immEnable) == 1
  val memRead2 = peek(dut.io.memRead) == 1
  val memWrite2 = peek(dut.io.memWrite) == 1
  val regWrite2 = peek(dut.io.regWrite) == 1
  val aluOrMem2 = peek(dut.io.aluOrMem) == 1
  val writeImmEnable2 = peek(dut.io.writeImmEnable) == 1
  val jump2 = peek(dut.io.jump) == 1
  val jumpEq2 = peek(dut.io.jumpEq) == 1
  expect(immEnable2, "Expected immEnable to be true, got " + immEnable2)
  expect(!memRead2, "Expected memRead to be false, got " + memRead2)
  expect(!memWrite2, "Expected memWrite to be false, got " + memWrite2)
  expect(regWrite2, "Expected regWrite to be true, got " + regWrite2)
  expect(!aluOrMem2, "Expected aluOrMem to be false, got " + aluOrMem2)
  expect(writeImmEnable2, "Expected writeImmEnable to be true, got " + writeImmEnable2)
  expect(!jump2, "Expected jump to be false, got " + jump2)
  expect(!jumpEq2, "Expected jumpEq to be false, got " + jumpEq2)
  step(5)

  // Test Store Data (SD)
  poke(dut.io.instr, "b010001_00_00000000_00000000_00000000".U)
  val immEnable3 = peek(dut.io.immEnable) == 1
  val memRead3 = peek(dut.io.memRead) == 1
  val memWrite3 = peek(dut.io.memWrite) == 1
  val regWrite3 = peek(dut.io.regWrite) == 1
  val aluOrMem3 = peek(dut.io.aluOrMem) == 1
  val writeImmEnable3 = peek(dut.io.writeImmEnable) == 1
  val jump3 = peek(dut.io.jump) == 1
  val jumpEq3 = peek(dut.io.jumpEq) == 1
  expect(!immEnable3, "Expected immEnable to be false, got " + immEnable3)
  expect(!memRead3, "Expected memRead to be false, got " + memRead3)
  expect(memWrite3, "Expected memWrite to be true, got " + memWrite3)
  expect(!regWrite3, "Expected regWrite to be false, got " + regWrite3)
  expect(!aluOrMem3, "Expected aluOrMem to be false, got " + aluOrMem3)
  expect(!writeImmEnable3, "Expected writeImmEnable to be false, got " + writeImmEnable3)
  expect(!jump3, "Expected jump to be false, got " + jump3)
  expect(!jumpEq3, "Expected jumpEq to be false, got " + jumpEq3)
  step(5)

  // Test Add (ADD)
  poke(dut.io.instr, "b000000_00_00000000_00000000_00000000".U)
  val immEnable4 = peek(dut.io.immEnable) == 1
  val memRead4 = peek(dut.io.memRead) == 1
  val memWrite4 = peek(dut.io.memWrite) == 1
  val regWrite4 = peek(dut.io.regWrite) == 1
  val aluOrMem4 = peek(dut.io.aluOrMem) == 1
  val writeImmEnable4 = peek(dut.io.writeImmEnable) == 1
  val jump4 = peek(dut.io.jump) == 1
  val jumpEq4 = peek(dut.io.jumpEq) == 1
  expect(!immEnable4, "Expected immEnable to be false, got " + immEnable4)
  expect(!memRead4, "Expected memRead to be false, got " + memRead4)
  expect(!memWrite4, "Expected memWrite to be false, got " + memWrite4)
  expect(regWrite4, "Expected regWrite to be true, got " + regWrite4)
  expect(aluOrMem4, "Expected aluOrMem to be true, got " + aluOrMem4)
  expect(!writeImmEnable4, "Expected writeImmEnable to be false, got " + writeImmEnable4)
  expect(!jump4, "Expected jump to be false, got " + jump4)
  expect(!jumpEq4, "Expected jumpEq to be false, got " + jumpEq4)
  step(5)

  // Test Subtract Immediate (SUBI)
  poke(dut.io.instr, "b001001_00_00000000_00000000_00000000".U)
  val immEnable5 = peek(dut.io.immEnable) == 1
  val memRead5 = peek(dut.io.memRead) == 1
  val memWrite5 = peek(dut.io.memWrite) == 1
  val regWrite5 = peek(dut.io.regWrite) == 1
  val aluOrMem5 = peek(dut.io.aluOrMem) == 1
  val writeImmEnable5 = peek(dut.io.writeImmEnable) == 1
  val jump5 = peek(dut.io.jump) == 1
  val jumpEq5 = peek(dut.io.jumpEq) == 1
  expect(immEnable5, "Expected immEnable to be true, got " + immEnable5)
  expect(!memRead5, "Expected memRead to be false, got " + memRead5)
  expect(!memWrite5, "Expected memWrite to be false, got " + memWrite5)
  expect(regWrite5, "Expected regWrite to be true, got " + regWrite5)
  expect(aluOrMem5, "Expected aluOrMem to be true, got " + aluOrMem5)
  expect(!writeImmEnable5, "Expected writeImmEnable to be false, got " + writeImmEnable5)
  expect(!jump5, "Expected jump to be false, got " + jump5)
  expect(!jumpEq5, "Expected jumpEq to be false, got " + jumpEq5)
  step(5)

  //test jump
  poke(dut.io.instr, "b100000_00_00000000_00000000_00000000".U)
  val immEnable6 = peek(dut.io.immEnable) == 1
  val memRead6 = peek(dut.io.memRead) == 1
  val memWrite6 = peek(dut.io.memWrite) == 1
  val regWrite6 = peek(dut.io.regWrite) == 1
  val aluOrMem6 = peek(dut.io.aluOrMem) == 1
  val writeImmEnable6 = peek(dut.io.writeImmEnable) == 1
  val jump6 = peek(dut.io.jump) == 1
  val jumpEq6 = peek(dut.io.jumpEq) == 1
  expect(!immEnable6, "Expected immEnable to be false, got " + immEnable6)
  expect(!memRead6, "Expected memRead to be false, got " + memRead6)
  expect(!memWrite6, "Expected memWrite to be false, got " + memWrite6)
  expect(!regWrite6, "Expected regWrite to be false, got " + regWrite6)
  expect(!aluOrMem6, "Expected aluOrMem to be false, got " + aluOrMem6)
  expect(!writeImmEnable6, "Expected writeImmEnable to be false, got " + writeImmEnable6)
  expect(jump6, "Expected jump to be true, got " + jump6)
  expect(!jumpEq6, "Expected jumpEq to be false, got " + jumpEq6)
  step(5)

  //test jump
  poke(dut.io.instr, "b100001_00_00000000_00000000_00000000".U)
  val immEnable7 = peek(dut.io.immEnable) == 1
  val memRead7 = peek(dut.io.memRead) == 1
  val memWrite7 = peek(dut.io.memWrite) == 1
  val regWrite7 = peek(dut.io.regWrite) == 1
  val aluOrMem7 = peek(dut.io.aluOrMem) == 1
  val writeImmEnable7 = peek(dut.io.writeImmEnable) == 1
  val jump7 = peek(dut.io.jump) == 1
  val jumpEq7 = peek(dut.io.jumpEq) == 1
  expect(!immEnable7, "Expected immEnable to be false, got " + immEnable7)
  expect(!memRead7, "Expected memRead to be false, got " + memRead7)
  expect(!memWrite7, "Expected memWrite to be false, got " + memWrite7)
  expect(!regWrite7, "Expected regWrite to be false, got " + regWrite7)
  expect(!aluOrMem7, "Expected aluOrMem to be false, got " + aluOrMem7)
  expect(!writeImmEnable7, "Expected writeImmEnable to be false, got " + writeImmEnable7)
  expect(!jump7, "Expected jump to be false, got " + jump7)
  expect(jumpEq7, "Expected jumpEq to be true, got " + jumpEq7)
  step(5)
}

object ControlUnitTester {
  def main(args: Array[String]): Unit = {
    println("Testing ControlUnit")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "ControlUnit"),
      () => new ControlUnit()) {
      c => new ControlUnitTester(c)
    }
  }
}
