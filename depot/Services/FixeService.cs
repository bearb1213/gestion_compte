using depot.Data;
using depot.Models;
using Microsoft.EntityFrameworkCore;

namespace depot.Services
{
    public class FixeService : IFixeService
    {
        private readonly AppDbContext _context;

        public FixeService(AppDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Fixe>> GetAllFixesAsync()
        {
            return await _context.Fixes.ToListAsync();
        }

        public async Task<Fixe?> GetFixeByIdAsync(int id)
        {
            return await _context.Fixes.FindAsync(id);
        }

        public async Task<Fixe> CreateFixeAsync(Fixe fixe)
        {
            _context.Fixes.Add(fixe);
            await _context.SaveChangesAsync();
            return fixe;
        }

        public async Task<Fixe?> UpdateFixeAsync(int id, Fixe fixe)
        {
            var existingFixe = await _context.Fixes.FindAsync(id);
            if (existingFixe == null)
                return null;

            existingFixe.Libelle = fixe.Libelle;
            existingFixe.Valeur = fixe.Valeur;

            await _context.SaveChangesAsync();
            return existingFixe;
        }

        public async Task<bool> DeleteFixeAsync(int id)
        {
            var fixe = await _context.Fixes.FindAsync(id);
            if (fixe == null)
                return false;

            _context.Fixes.Remove(fixe);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}