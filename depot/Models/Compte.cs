using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace depot.Models
{
    [Table("compte")] 
    public class Compte
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }
        
        [Required]
        [Column("id_particulier")]
        public int IdParticulier { get; set; }
        
        [Required]
        [Column("numero")]
        [StringLength(50)]
        public string Numero { get; set; } = string.Empty;
        
        [Column("date_ouverture")]
        public DateTime? DateOuverture { get; set; }
        
        // Navigation properties
        // public virtual ICollection<Transaction> Transactions { get; set; } = new List<Transaction>();
        // public virtual ICollection<HistoStatus> HistoStatus { get; set; } = new List<HistoStatus>();
        // public virtual ICollection<Frais> Frais { get; set; } = new List<Frais>();
    }
}