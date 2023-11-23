import chisel3._
import chisel3.util._

class FIFO[T <: Data](ioType: T, depth: Int) extends Module {
  val io = IO(new Bundle{
    val in = Flipped(Decoupled(ioType))
    val out = Decoupled(ioType)
    val full = Output(Bool())
    val empty = Output(Bool())
  })
  io.out <> Queue(io.in, depth)
  io.full := !io.in.ready
  io.empty := !io.out.valid
}
