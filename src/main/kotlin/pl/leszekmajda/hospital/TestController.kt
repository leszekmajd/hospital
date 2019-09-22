package pl.leszekmajda.hospital

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TestController {

    @GetMapping("/")
    fun hospital(model: Model): String {
        model["title"] = "Hospital_Test"
        return "hospital"

    }
}
