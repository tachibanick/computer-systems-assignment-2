import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(16.W))
    val b = Input(UInt(16.W))
    val op = Input(UInt(3.W))
    val result = Output(UInt(16.W))
    val zero = Output(Bool())
  })

  io.result := 0.U
  io.zero := false.B

  switch(io.op) {
    is(0.U) { io.result := io.a + io.b }
    is(1.U) { io.result := io.a - io.b }
    is(2.U) { io.result := io.a * io.b }
  }

  when(io.result === 0.U) {
    io.zero := true.B
  }
}