package es.upm.dit.isst.grupo01.medcon01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.upm.dit.isst.grupo01.medcon01.model.Paciente;
import es.upm.dit.isst.grupo01.medcon01.repository.MedConRepository;

@Controller
public class ControllerKiosko {
  
    @GetMapping("/")
    public String showIndexPage() {
        return "inicio_kiosko";
    }
    
    @GetMapping("/error")
public String showErrorPage() {
    return "error";
}

    @GetMapping("/inicio_kiosko")
    public String showLoginPage() {
        return "incio_kiosko";
    }

    @PostMapping("/login_DNI")
public String registrarPaciente(@ModelAttribute Paciente paciente, Model model) {
    // Validar el formato del DNI
    if (!validarDNI(paciente.getDNI())) {
        model.addAttribute("error", "El DNI no tiene un formato válido.");
        return "inicio_kiosko";
    }
    // Guardar el usuario en la base de datos
    MedConRepository.save(paciente);

    // Redirigir al usuario a la página con la información de su cita
    return "redirect:/informacion_cita";
}
//comprueba que la longitud del DNI sea de 9 caracteres y que el último carácter sea una letra
public boolean validarDNI(String dni) {
    String letraMayuscula = "";
    if (dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false) {
        return false;
    }
    letraMayuscula = dni.substring(8).toUpperCase();
    if (soloNumeros(dni) == true && letraDNI(dni).equals(letraMayuscula)) {
        return true;
    } else {
        return false;
    }
}
// comprobar que los primeros 8 caracteres del DNI son números
private boolean soloNumeros(String dni) {
    int i, j = 0;
    String numero = "";
    String miDNI = "";
    String[] unoNueve = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    for (i = 0; i < dni.length() - 1; i++) {
        numero = dni.substring(i, i + 1);
        for (j = 0; j < unoNueve.length; j++) {
            if (numero.equals(unoNueve[j])) {
                miDNI += unoNueve[j];
            }
        }
    }
    if (miDNI.length() != 8) {
        return false;
    } else {
        return true;
    }
}

private String letraDNI(String dni) {
    int miDNI = Integer.parseInt(dni.substring(0, 8));
    int resto = 0;
    String miLetra = "";
    String[] asignacionLetra = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q",
            "V", "H", "L", "C", "K", "E" };
    resto = miDNI % 23;
    miLetra = asignacionLetra[resto];
    return miLetra;
}


    @GetMapping("/welcome")
    public String showWelcomePage(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "welcome";
    }
}
