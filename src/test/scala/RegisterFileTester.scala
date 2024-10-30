import chisel3._
import chisel3.iotesters.PeekPokeTester

class RegisterFileTester(dut: RegisterFile) extends PeekPokeTester(dut) {
  // Write 48 to register 3
  poke(dut.io.writeData, 48)
  poke(dut.io.writeSel, 3)
  poke(dut.io.writeEnable, 1)
  step(1)
  poke(dut.io.writeEnable, 0)
  step(5)

  // Write 17 to register 7
  poke(dut.io.writeData, 17)
  poke(dut.io.writeSel, 7)
  poke(dut.io.writeEnable, 1)
  step(1)
  poke(dut.io.writeEnable, 0)
  step(5)

  // Read from register 3 and 7
  poke(dut.io.aSel, 3)
  poke(dut.io.bSel, 7)
  val a = peek(dut.io.a)
  val b = peek(dut.io.b)
  expect(a == 48, "Expected a to be 48, got " + a)
  expect(b == 17, "Expected b to be 17, got " + b)
  step(5)

  // Write 32 to register 3
  poke(dut.io.writeData, 875)
  poke(dut.io.writeSel, 3)
  poke(dut.io.writeEnable, 1)
  step(1)
  poke(dut.io.writeEnable, 0)
  step(5)

  // Read from register 3 and 15
  poke(dut.io.aSel, 3)
  poke(dut.io.bSel, 15)
  val a2 = peek(dut.io.a)
  val b2 = peek(dut.io.b)
  expect(a2 == 875, "Expected a to be 875, got " + a2)
  expect(b2 == 0, "Expected b to be 0, got " + b2)
  step(5)
}

object RegisterFileTester {
  def main(args: Array[String]): Unit = {
    println("Testing RegisterFile")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "RegisterFile"),
      () => new RegisterFile()) {
      c => new RegisterFileTester(c)
    }
  }
}
