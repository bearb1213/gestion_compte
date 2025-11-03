using Microsoft.EntityFrameworkCore;
using depot.Models;

namespace depot.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
        {
        }

        public DbSet<Compte> Comptes { get; set; }
        public DbSet<Fixe> Fixes { get; set; }
        public DbSet<TypeTransaction> TypeTransactions { get; set; }
        public DbSet<Transaction> Transactions { get; set; }
        public DbSet<Status> Status { get; set; }
        public DbSet<HistoStatus> HistoStatus { get; set; }
        public DbSet<Frais> Frais { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // Spécifier les noms de tables
            modelBuilder.Entity<Compte>().ToTable("compte");
            modelBuilder.Entity<Fixe>().ToTable("fixe");
            modelBuilder.Entity<TypeTransaction>().ToTable("type_transaction");
            modelBuilder.Entity<Transaction>().ToTable("transaction");
            modelBuilder.Entity<Status>().ToTable("status");
            modelBuilder.Entity<HistoStatus>().ToTable("histo_status");
            modelBuilder.Entity<Frais>().ToTable("frais");

            // Configuration pour Compte
            modelBuilder.Entity<Compte>(entity =>
            {
                entity.HasIndex(c => c.Numero).IsUnique();
            });

            // Configuration pour Transaction
            modelBuilder.Entity<Transaction>(entity =>
            {
                entity.Property(t => t.Montant).HasColumnType("decimal(15,2)");
                
                // entity.HasOne(t => t.TypeTransaction)
                //     .WithMany(tt => tt.Transactions)
                //     .HasForeignKey(t => t.IdType)
                //     .OnDelete(DeleteBehavior.SetNull);

                // entity.HasOne(t => t.Compte)
                //     .WithMany(c => c.Transactions)
                //     .HasForeignKey(t => t.IdCompte)
                //     .OnDelete(DeleteBehavior.Cascade);
            });

            // Configuration pour Frais
            modelBuilder.Entity<Frais>(entity =>
            {
                entity.Property(f => f.Valeur).HasColumnType("decimal(15,2)");
            });

            base.OnModelCreating(modelBuilder);
        }
    }
}