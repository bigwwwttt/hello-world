package fifo

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class FIFOTest extends AnyFlatSpec with ChiselScalatestTester {
    "FIFO" should "pass basic tests" in {
        test(new FIFO(UInt(8.W), 8)){dut =>
        //test write
        dut.io.out.ready.poke(false.B)
        dut.io.in.valid.poke(true.B)
        for(i <- 0 until 8){
            val datain = i * i + 1
            dut.io.in.bits.poke((datain.U))//write data
            dut.clock.step(1)
        }
        dut.io.full.expect(1.B)//test full, expect full = 1
        println("write test success")

        //test read
        dut.io.out.ready.poke(true.B)
        dut.io.in.valid.poke(false.B)
        for(i <- 0 until 8){
            val datain = i * i + 1
            dut.io.out.bits.expect((datain.U))//read data
            dut.clock.step(1)
        }
        dut.io.empty.expect(1.B)//test empty, expect empty = 1
        println("read test success")

        //test Simultaneous read and write
        dut.io.out.ready.poke(true.B)
        dut.io.in.valid.poke(true.B)
        for(i <- 0 until 8){
            val datain = i * i + 1
            dut.io.in.bits.poke((datain.U))
            dut.clock.step(1)
            dut.io.out.bits.expect((datain.U))
        }
        println("write and read test success")
        }
    }    

}


