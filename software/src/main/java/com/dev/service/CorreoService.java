package com.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.dev.domain.Correo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


@Service
public class CorreoService {


	    @Autowired
	    private JavaMailSender mailSender;

	    @Autowired
	    private TemplateEngine templateEngine;

	    public void enviarCorreo(Correo correo) {
	        Context context = new Context();
	        context.setVariables(correo.getModel());
	        String contenido = templateEngine.process("correo-alquiler", context);

	        MimeMessagePreparator preparator = mimeMessage -> {
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	            helper.setFrom(correo.getDe());
	            helper.setTo(correo.getDestinario());
	            helper.setSubject(correo.getAsunto());
	            helper.setText(contenido, true);
	            
	            Resource resource = new ClassPathResource("/static/logo_empresa.png");
	            helper.addInline("logoEmpresa", resource);
	        };

	        mailSender.send(preparator);
	    }
	

}
