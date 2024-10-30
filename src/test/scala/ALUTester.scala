import chisel3._
import chisel3.iotesters.PeekPokeTester

class ALUTester(dut: ALU) extends PeekPokeTester(dut) {
  // Calculate 12 + 9
  poke(dut.io.a, 12)
  poke(dut.io.b, 9)
  poke(dut.io.op, 0)
  val result = peek(dut.io.result)
  val zero = peek(dut.io.zero) == 1
  expect(result == 21, "Expected result to be 21, got " + result)
  expect(!zero, "Expected zero to be false, got " + zero)
  step(5)

  // Calculate 845 - 611
  poke(dut.io.a, 845)
  poke(dut.io.b, 611)
  poke(dut.io.op, 1)
  val result2 = peek(dut.io.result)
  val zero2 = peek(dut.io.zero) == 1
  expect(result2 == 234, "Expected result to be 234, got " + result2)
  expect(!zero2, "Expected zero to be false, got " + zero2)
  step(5)

  // Calculate 11 * 13
  poke(dut.io.a, 11)
  poke(dut.io.b, 13)
  poke(dut.io.op, 2)
  val result3 = peek(dut.io.result)
  val zero3 = peek(dut.io.zero) == 1
  expect(result3 == 143, "Expected result to be 143, got " + result3)
  expect(!zero3, "Expected zero to be false, got " + zero3)
  step(5)

  // Calculate 14 - 14 to test the zero check
  poke(dut.io.a, 14)
  poke(dut.io.b, 14)
  poke(dut.io.op, 1)
  val result4 = peek(dut.io.result)
  val zero4 = peek(dut.io.zero) == 1
  expect(result4 == 0, "Expected result to be 0, got " + result4)
  expect(zero4, "Expected zero to be true, got " + zero4)
  step(5)
}

object ALUTester {
  def main(args: Array[String]): Unit = {
    println("Testing ALU")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "ALU"),
      () => new ALU()) {
      c => new ALUTester(c)
    }
  }
}
