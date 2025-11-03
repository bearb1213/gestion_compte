using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace depot.Models
{
    [Table("transaction")] 
    public class Transaction
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }
        
        [Required]
        [Column("montant",TypeName = "decimal(15,2)")]
        public decimal Montant { get; set; }
        
        [Required]
        [Column("date_transaction")]
        public DateTime DateTransaction { get; set; }
        
        [Column("description")]
        public string? Description { get; set; }
        
        [Required]
        [Column("mouvement")]
        [StringLength(1)]
        public char Mouvement { get; set; }
        
        // Foreign keys
        [Column("id_type")]
        public int? IdType { get; set; }
        
        [Required]
        [Column("id_compte")]
        public int IdCompte { get; set; }
        
        // Navigation properties
        [ForeignKey("IdType")]
        public virtual TypeTransaction? TypeTransaction { get; set; }
        
        [ForeignKey("IdCompte")]
        public virtual Compte Compte { get; set; } = null!;
    }
}