import chisel3._
import chisel3.util._

class RegisterFile extends Module {
  val io = IO(new Bundle {
    val aSel = Input(UInt(4.W))
    val bSel = Input(UInt(4.W))

    val writeData = Input(UInt(32.W))
    val writeSel = Input(UInt(4.W))
    val writeEnable = Input(Bool())

    val a = Output(UInt(32.W))
    val b = Output(UInt(32.W))
  })

  // Initialize the register file with 16 registers, each 32 bits wide
  val regFile = RegInit(VecInit(Seq.fill(16)(0.U(32.W))))

  io.a := regFile(io.aSel)
  io.b := regFile(io.bSel)

  when(io.writeEnable) {
    regFile(io.writeSel) := io.writeData
  }
}