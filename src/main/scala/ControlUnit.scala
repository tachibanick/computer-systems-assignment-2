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

    val aluOrMem = Output(Bool())
    val writeImmEnable = Output(Bool()) // Write imm to register or output of ALU or memory

    val jump = Output(Bool())
    val jumpEq = Output(Bool())
  })

  io.immEnable := false.B
  io.memRead := false.B
  io.memWrite := false.B
  io.regWrite := false.B
  io.aluOrMem := false.B
  io.writeImmEnable := false.B
  io.jump := false.B
  io.jumpEq := false.B

  val opCode = io.instr(31, 26) // Extract first 6 bits from instruction to get op code

  io.immEnable := opCode(3) // Fourth bit (from the right) of op code determines if we use immediate

  io.memRead := opCode === "b010000".U // LD
  io.memWrite := opCode === "b010001".U // SD
  io.writeImmEnable := opCode === "b011000".U// LI
  io.jump := opCode === "b100000".U // jump
  io.jumpEq := opCode === "b100001".U //jumpEq
  io.aluOrMem := opCode(5,4) === "b00".U //use ALU result not memory result

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