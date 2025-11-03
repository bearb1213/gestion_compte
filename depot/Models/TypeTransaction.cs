using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace depot.Models
{
    [Table("type_transaction")] 
    public class TypeTransaction
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }
        
        [Required]
        [Column("libelle")]
        [StringLength(50)]
        public string Libelle { get; set; } = string.Empty;
        
        // Navigation property
        // public virtual ICollection<Transaction> Transactions { get; set; } = new List<Transaction>();
    }
}