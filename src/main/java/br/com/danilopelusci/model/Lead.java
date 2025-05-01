package br.com.danilopelusci.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "leads")
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrigemEnum origem;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('NOVO', 'EM_ATENDIMENTO', 'FINALIZADO') DEFAULT 'NOVO'")
    private StatusEnum statusLeads = StatusEnum.NOVO;
    

    @ManyToOne
    @JoinColumn(name = "corretor_id", foreignKey = @ForeignKey(name = "fk_lead_corretor"))
    private Usuario usuario;

    public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public OrigemEnum getOrigem() { return origem; }
    public void setOrigem(OrigemEnum origem) { this.origem = origem; }
	public StatusEnum getStatus() {
		return statusLeads;
	}
	public void setStatus(StatusEnum status) {
		this.statusLeads = status;
	}

   
}
