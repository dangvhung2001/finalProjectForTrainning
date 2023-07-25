package com.example.finalproject.Controller;

import com.example.finalproject.service.CertificateService;
import com.example.finalproject.service.dto.CertificateDTO;
import com.example.finalproject.service.dto.SkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/index")
    public String listCertificates(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<CertificateDTO> certificates = certificateService.findAll(pageable);
        model.addAttribute("certificates", certificates);
        return "certificate/list";
    }

    @GetMapping("/{id}")
    public String viewCertificate(@PathVariable Long id, Model model) {
        Optional<CertificateDTO> certificate = certificateService.findOne(id);
        model.addAttribute("certificate", certificate.orElse(null));
        return "certificate/view";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("certificateDTO", new CertificateDTO());
        return "certificate/create";
    }

    @PostMapping("/create")
    public String createCertificate(@ModelAttribute("certificateDTO") CertificateDTO certificateDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "certificate/create";
        }
        certificateService.save(certificateDTO);
        return "redirect:/certificates/index";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<CertificateDTO> certificate = certificateService.findOne(id);
        model.addAttribute("certificateDTO", certificate.orElse(null));
        return "certificate/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCertificate(@PathVariable Long id, @ModelAttribute("certificateDTO") CertificateDTO certificateDTO, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            return "certificate/edit";
        }
        certificateDTO.setId(id);
        certificateService.save(certificateDTO);
        return "redirect:/certificates";
    }

    @PostMapping("/delete/{id}")
    public String deleteCertificate(@PathVariable Long id) {
        certificateService.delete(id);
        return "redirect:/certificates";
    }
}

