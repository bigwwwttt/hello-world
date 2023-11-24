package fifo

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class FIFOTest extends AnyFlatSpec with ChiselScalatestTester {
    "FIFO" should "pass basic tests" in {
        test(new FIFO(UInt(8.W), 8)){dut =>
        dut.io.out.ready.poke(false.B)
        dut.io.in.valid.poke(true.B)
        println(s"Staring:")
        println(s"\tfull=${dut.io.full.peek().litValue}, empty=${dut.io.empty.peek().litValue}")
        println(s"\twidx=${dut.io.widx.peek().litValue}, ridx=${dut.io.ridx.peek().litValue}")

        println("testing write:")
        for(i <- 0 until 8){
            val datain = i * i + 1
            dut.io.in.bits.poke((datain.U))
            dut.clock.step(1)
            println(s"$i write:")
            println(s"\tfull=${dut.io.full.peek().litValue}, empty=${dut.io.empty.peek().litValue}")
            println(s"\twidx=${dut.io.widx.peek().litValue}, ridx=${dut.io.ridx.peek().litValue}")
        }
        dut.io.full.expect(1.B)
        println("write test success")

        println("testing read:")
        dut.io.out.ready.poke(true.B)
        dut.io.in.valid.poke(false.B)
        for(i <- 0 until 8){
            val datain = i * i + 1
            dut.io.out.bits.expect((datain.U))
            dut.clock.step(1)
            println(s"$i read:")
            println(s"\tfull=${dut.io.full.peek().litValue}, empty=${dut.io.empty.peek().litValue}")
            println(s"\tbits=${dut.io.out.bits.peek().litValue}")
            println(s"\twidx=${dut.io.widx.peek().litValue}, ridx=${dut.io.ridx.peek().litValue}")
    }
        dut.io.empty.expect(1.B)
        println("read test success")
    

        dut.io.out.ready.poke(true.B)
        dut.io.in.valid.poke(true.B)
        println("testing write and read:")
        for(i <- 0 until 8){
            val datain = i * i + 1
            dut.io.in.bits.poke((datain.U))
            dut.clock.step(1)
            dut.io.out.bits.expect((datain.U))
            println(s"\twidx=${dut.io.widx.peek().litValue}, ridx=${dut.io.ridx.peek().litValue}")
        }
        println("write and read test success")
    
        }

    }
}


