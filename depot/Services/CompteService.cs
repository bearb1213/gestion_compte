using depot.Data;
using depot.Models;
using Microsoft.EntityFrameworkCore;

namespace depot.Services
{
    public class CompteService : ICompteService
    {
        private readonly AppDbContext _context;

        public CompteService(AppDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Compte>> GetAllComptesAsync()
        {
            return await _context.Comptes.ToListAsync();
        }

        public async Task<Compte?> GetCompteByIdAsync(int id)
        {
            return await _context.Comptes.FindAsync(id);
        }

        public async Task<Compte> CreateCompteAsync(Compte compte)
        {
            _context.Comptes.Add(compte);
            await _context.SaveChangesAsync();
            return compte;
        }

        public async Task<Compte?> UpdateCompteAsync(int id, Compte compte)
        {
            var existingCompte = await _context.Comptes.FindAsync(id);
            if (existingCompte == null)
                return null;

            existingCompte.IdParticulier = compte.IdParticulier;
            existingCompte.Numero = compte.Numero;
            existingCompte.DateOuverture = compte.DateOuverture;

            await _context.SaveChangesAsync();
            return existingCompte;
        }

        public async Task<bool> DeleteCompteAsync(int id)
        {
            var compte = await _context.Comptes.FindAsync(id);
            if (compte == null)
                return false;

            _context.Comptes.Remove(compte);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}