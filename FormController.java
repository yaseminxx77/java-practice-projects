
@Controller
public class FormController {





    @GetMapping("/form")
    public String showForm() {
        return "form";
    }

    @PostMapping("/form")
    public String handleForm(@RequestParam String firstname,
                             @RequestParam String lastname,
                             @RequestParam String email,
                             Model model) {
        // Verileri HTML'e taşımak için modele ekle
        model.addAttribute("firstname", firstname);
        model.addAttribute("lastname", lastname);
        model.addAttribute("email", email);

        return "result";
    }
}

