using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace depot.Models
{
    [Table("status")] 
    public class Status
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }
        
        [Required]
        [Column("libelle")]
        [StringLength(50)]
        public string Libelle { get; set; } = string.Empty;
        
        // Navigation property
        // public virtual ICollection<HistoStatus> HistoStatus { get; set; } = new List<HistoStatus>();
    }
}