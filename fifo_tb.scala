import chisel3._
import chiseltest._
import org.scalatest._
import chisel3.experimental._

class FIFOTest extends FlatSpec with ChiselScalatestTester {
    "FIFO" should "pass basic tests" in {
        test(new FIFO(UInt(8.W), 8)){c =>
        //test write
        c.io.out.ready.poke(false.B)
        c.io.in.valid.poke(true.B)
        for(i <- 0 until 8){
            val datain = i * i + 1
            c.io.in.bits.poke((datain.U))//write data
            c.clock.step(1)
        }
        c.io.full.expect(1.B)//test full, expect full = 1
        println("write test success")

        //test read
        c.io.out.ready.poke(true.B)
        c.io.in.valid.poke(false.B)
        for(i <- 0 until 8){
            val datain = i * i + 1
            c.io.out.bits.expect((datain.U))//read data
            c.clock.step(1)
        }
        c.io.empty.expect(1.B)//test empty, expect empty = 1
        println("read test success")

        //test Simultaneous read and write
        c.io.out.ready.poke(true.B)
        c.io.in.valid.poke(true.B)
        for(i <- 0 until 8){
            val datain = i * i + 1
            c.io.in.bits.poke((datain.U))
            c.clock.step(1)
            c.io.out.bits.expect((datain.U))
        }
        println("write and read test success")
        }
    }    

}
