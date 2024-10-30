import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    val instr = Input(UInt(32.W))

//    val immVal = Output(UInt(32.W))
    val immEnable = Output(Bool())

//    val aluOp = Output(UInt(3.W))

    val memRead = Output(Bool())
    val memWrite = Output(Bool())

    val regWrite = Output(Bool())
  })

  io.immEnable := false.B
  io.memRead := false.B
  io.memWrite := false.B
  io.regWrite := false.B

  val opCode = io.instr(31, 26) // Extract first 6 bits from instruction to get op code

  io.immEnable := opCode(3) // Fourth bit (from the right) of op code determines if we use immediate

  io.memRead := opCode === "b010000".U // LD
  io.memWrite := opCode === "b010001".U // SD

  when (opCode(5,4) === "b00".U) {
    // All ALU operations write to a register
    io.regWrite := true.B
  } .elsewhen ((opCode & "b110111".U) === "b010000".U) {
    // Both LD and LI also write to a register
    io.regWrite := true.B
  } .otherwise {
    io.regWrite := false.B
  }
}