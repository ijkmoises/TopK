package br.com.emesistemas.topk.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtensionKtTest{

    @Test
    fun should_convert_date_to_ddMMyyyy(){

        var dateText = "2014-04-01T22:40:40Z"
        assertEquals(ddMMyy(dateText),"01/04/2014")

        dateText = "2020-09-02T17:25:41Z"
        assertEquals(ddMMyy(dateText),"02/09/2020")

        dateText = "2020-08-31T08:19:08Z"
        assertEquals(ddMMyy(dateText),"31/08/2020")
    }


    @Test
    fun should_not_rconvert_date_to_ddMMyyyy_when_input_is_worng(){

        var dateText = "2014-04-f01T22:40:40Z"
        assertEquals(ddMMyy(dateText),"-")

        dateText = "2020-09-02T1725:41Z"
        assertEquals(ddMMyy(dateText),"-")

        dateText = "2020-08-31H08:19:08Z"
        assertEquals(ddMMyy(dateText),"-")
    }

}