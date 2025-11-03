using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace depot.Models
{
    [Table("histo_status")] 
    public class HistoStatus
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }
        
        [Required]
        [Column("date_changement")]
        public DateTime DateChangement { get; set; }
        
        // Foreign keys
        [Required]
        [Column("id_compte")]
        public int IdCompte { get; set; }
        
        [Required]
        [Column("id_status")]
        public int IdStatus { get; set; }
        
        // Navigation properties
        [ForeignKey("IdCompte")]
        public virtual Compte Compte { get; set; } = null!;
        
        [ForeignKey("IdStatus")]
        public virtual Status Status { get; set; } = null!;
    }
}