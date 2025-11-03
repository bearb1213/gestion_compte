using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace depot.Models
{
    [Table("frais")] 
    public class Frais
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }
        
        [Required]
        [Column("libelle")]
        [StringLength(50)]
        public string Libelle { get; set; } = string.Empty;
        
        [Required]
        [Column("valeur",TypeName = "decimal(15,2)")]
        public decimal Valeur { get; set; }
        
        [Required]
        [Column("date_changement")]
        public DateTime DateChangement { get; set; }
        
        [Required]
        [Column("supprime")]
        [StringLength(1)]
        public char Supprime { get; set; }
        
        // Foreign key
        [Required]
        [Column("id_compte")]
        public int IdCompte { get; set; }
        
        // Navigation property
        [ForeignKey("IdCompte")]
        public virtual Compte Compte { get; set; } = null!;
    }
}