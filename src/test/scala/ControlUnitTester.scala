import chisel3._
import chisel3.iotesters.PeekPokeTester

class ControlUnitTester(dut: ControlUnit) extends PeekPokeTester(dut) {
  // Test Load Data (LD)
  poke(dut.io.instr, "b010000_00_00000000_00000000_00000000".U)
  val immEnable = peek(dut.io.immEnable) == 1
  val memRead = peek(dut.io.memRead) == 1
  val memWrite = peek(dut.io.memWrite) == 1
  val regWrite = peek(dut.io.regWrite) == 1
  expect(!immEnable, "Expected immEnable to be false, got " + immEnable)
  expect(memRead, "Expected memRead to be true, got " + memRead)
  expect(!memWrite, "Expected memWrite to be false, got " + memWrite)
  expect(regWrite, "Expected regWrite to be true, got " + regWrite)
  step(5)

  // Test Load Immediate (LI)
  poke(dut.io.instr, "b011000_00_00000000_00000000_00000000".U)
  val immEnable2 = peek(dut.io.immEnable) == 1
  val memRead2 = peek(dut.io.memRead) == 1
  val memWrite2 = peek(dut.io.memWrite) == 1
  val regWrite2 = peek(dut.io.regWrite) == 1
  expect(immEnable2, "Expected immEnable to be true, got " + immEnable2)
  expect(!memRead2, "Expected memRead to be false, got " + memRead2)
  expect(!memWrite2, "Expected memWrite to be false, got " + memWrite2)
  expect(regWrite2, "Expected regWrite to be true, got " + regWrite2)
  step(5)

  // Test Store Data (SD)
  poke(dut.io.instr, "b010001_00_00000000_00000000_00000000".U)
  val immEnable3 = peek(dut.io.immEnable) == 1
  val memRead3 = peek(dut.io.memRead) == 1
  val memWrite3 = peek(dut.io.memWrite) == 1
  val regWrite3 = peek(dut.io.regWrite) == 1
  expect(!immEnable3, "Expected immEnable to be false, got " + immEnable3)
  expect(!memRead3, "Expected memRead to be false, got " + memRead3)
  expect(memWrite3, "Expected memWrite to be true, got " + memWrite3)
  expect(!regWrite3, "Expected regWrite to be false, got " + regWrite3)
  step(5)

  // Test Add (ADD)
  poke(dut.io.instr, "b000000_00_00000000_00000000_00000000".U)
  val immEnable4 = peek(dut.io.immEnable) == 1
  val memRead4 = peek(dut.io.memRead) == 1
  val memWrite4 = peek(dut.io.memWrite) == 1
  val regWrite4 = peek(dut.io.regWrite) == 1
  expect(!immEnable4, "Expected immEnable to be false, got " + immEnable4)
  expect(!memRead4, "Expected memRead to be false, got " + memRead4)
  expect(!memWrite4, "Expected memWrite to be false, got " + memWrite4)
  expect(regWrite4, "Expected regWrite to be true, got " + regWrite4)
  step(5)

  // Test Subtract Immediate (SUBI)
  poke(dut.io.instr, "b001001_00_00000000_00000000_00000000".U)
  val immEnable5 = peek(dut.io.immEnable) == 1
  val memRead5 = peek(dut.io.memRead) == 1
  val memWrite5 = peek(dut.io.memWrite) == 1
  val regWrite5 = peek(dut.io.regWrite) == 1
  expect(immEnable5, "Expected immEnable to be true, got " + immEnable5)
  expect(!memRead5, "Expected memRead to be false, got " + memRead5)
  expect(!memWrite5, "Expected memWrite to be false, got " + memWrite5)
  expect(regWrite5, "Expected regWrite to be true, got " + regWrite5)
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
