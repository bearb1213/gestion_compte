using depot.Data;
using depot.Models;
using Microsoft.EntityFrameworkCore;

namespace depot.Services
{
    public class FraisService : IFraisService
    {
        private readonly AppDbContext _context;

        public FraisService(AppDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Frais>> GetAllFraisAsync()
        {
            return await _context.Frais
                .Include(f => f.Compte)
                .ToListAsync();
        }

        public async Task<Frais?> GetFraisByIdAsync(int id)
        {
            return await _context.Frais
                .Include(f => f.Compte)
                .FirstOrDefaultAsync(f => f.Id == id);
        }

        public async Task<Frais> CreateFraisAsync(Frais frais)
        {
            _context.Frais.Add(frais);
            await _context.SaveChangesAsync();
            return frais;
        }

        public async Task<Frais?> UpdateFraisAsync(int id, Frais frais)
        {
            var existingFrais = await _context.Frais.FindAsync(id);
            if (existingFrais == null)
                return null;

            existingFrais.Libelle = frais.Libelle;
            existingFrais.Valeur = frais.Valeur;
            existingFrais.DateChangement = frais.DateChangement;
            existingFrais.Supprime = frais.Supprime;
            existingFrais.IdCompte = frais.IdCompte;

            await _context.SaveChangesAsync();
            return existingFrais;
        }

        public async Task<bool> DeleteFraisAsync(int id)
        {
            var frais = await _context.Frais.FindAsync(id);
            if (frais == null)
                return false;

            _context.Frais.Remove(frais);
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<IEnumerable<Frais>> GetFraisByCompteIdAsync(int compteId)
        {
            return await _context.Frais
                .Where(f => f.IdCompte == compteId)
                .ToListAsync();
        }
    }
}