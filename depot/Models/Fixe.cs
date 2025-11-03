using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace depot.Models
{
    [Table("fixe")] 
    public class Fixe
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }
        
        [Required]
        [Column("libelle")]
        [StringLength(50)]
        public string Libelle { get; set; } = string.Empty;
        
        [Required]
        [Column("valeur",TypeName = "decimal(4,2)")]
        public decimal Valeur { get; set; }
    }
}