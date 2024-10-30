import chisel3._
import chisel3.util._

class CPUTop extends Module {
  val io = IO(new Bundle {
    val done = Output(Bool ())
    val run = Input(Bool ())
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerDataMemEnable = Input(Bool ())
    val testerDataMemAddress = Input(UInt (16.W))
    val testerDataMemDataRead = Output(UInt (32.W))
    val testerDataMemWriteEnable = Input(Bool ())
    val testerDataMemDataWrite = Input(UInt (32.W))
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerProgMemEnable = Input(Bool ())
    val testerProgMemAddress = Input(UInt (16.W))
    val testerProgMemDataRead = Output(UInt (32.W))
    val testerProgMemWriteEnable = Input(Bool ())
    val testerProgMemDataWrite = Input(UInt (32.W))
  })

  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())

  //Connecting the modules
  programCounter.io.run := io.run
  programMemory.io.address := programCounter.io.programCounter

  val instr = programMemory.io.instructionRead

  registerFile.io.writeSel := instr(25, 22)
  registerFile.io.aSel := instr(21, 18)
  registerFile.io.bSel := Mux(controlUnit.io.jumpEq, instr(25, 22), instr(17, 14))
  registerFile.io.writeEnable := controlUnit.io.regWrite

  val immVal = instr(15, 0)
  val bOrImm = Mux(controlUnit.io.immEnable, immVal, registerFile.io.b)

  alu.io.op := instr(28, 26) // Last 3 bits of the op code
  alu.io.a := registerFile.io.a
  alu.io.b := bOrImm

  dataMemory.io.address := bOrImm
  dataMemory.io.dataWrite := registerFile.io.a
  dataMemory.io.writeEnable := controlUnit.io.memWrite

  val aluOrMemResult = Mux(controlUnit.io.aluOrMem, alu.io.result, dataMemory.io.dataRead)
  registerFile.io.writeData := Mux(controlUnit.io.writeImmEnable, immVal, aluOrMemResult)

  programCounter.io.programCounterJump := immVal
  programCounter.io.jump := controlUnit.io.jump || (controlUnit.io.jumpEq && alu.io.zero)



  //This signals are used by the tester for loading the program to the program memory, do not touch
  programMemory.io.testerAddress := io.testerProgMemAddress
  io.testerProgMemDataRead := programMemory.io.testerDataRead
  programMemory.io.testerDataWrite := io.testerProgMemDataWrite
  programMemory.io.testerEnable := io.testerProgMemEnable
  programMemory.io.testerWriteEnable := io.testerProgMemWriteEnable
  //This signals are used by the tester for loading and dumping the data memory content, do not touch
  dataMemory.io.testerAddress := io.testerDataMemAddress
  io.testerDataMemDataRead := dataMemory.io.testerDataRead
  dataMemory.io.testerDataWrite := io.testerDataMemDataWrite
  dataMemory.io.testerEnable := io.testerDataMemEnable
  dataMemory.io.testerWriteEnable := io.testerDataMemWriteEnable
}