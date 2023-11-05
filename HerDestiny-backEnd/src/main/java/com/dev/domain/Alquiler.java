package com.dev.domain;

import java.time.LocalDateTime;
import java.time.YearMonth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ALQUILER")
public class Alquiler {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALQUILER")
    private Long idAlquiler;
    
    
    private LocalDateTime fcAlquiler;
    
    private String numTarjeta;
    
    private String fcVencimiento;
    
    private String cvv;
    
    private String titularTj;
    
    
	@ManyToOne
	@JoinColumn(name="ID_PRODUCTO")
    private Producto producto;

}