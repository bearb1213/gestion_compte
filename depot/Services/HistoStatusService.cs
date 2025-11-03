using depot.Data;
using depot.Models;
using Microsoft.EntityFrameworkCore;

namespace depot.Services
{
    public class HistoStatusService : IHistoStatusService
    {
        private readonly AppDbContext _context;

        public HistoStatusService(AppDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<HistoStatus>> GetAllHistoStatusAsync()
        {
            return await _context.HistoStatus
                .Include(h => h.Compte)
                .Include(h => h.Status)
                .ToListAsync();
        }

        public async Task<HistoStatus?> GetHistoStatusByIdAsync(int id)
        {
            return await _context.HistoStatus
                .Include(h => h.Compte)
                .Include(h => h.Status)
                .FirstOrDefaultAsync(h => h.Id == id);
        }

        public async Task<HistoStatus> CreateHistoStatusAsync(HistoStatus histoStatus)
        {
            _context.HistoStatus.Add(histoStatus);
            await _context.SaveChangesAsync();
            return histoStatus;
        }

        public async Task<HistoStatus?> UpdateHistoStatusAsync(int id, HistoStatus histoStatus)
        {
            var existingHistoStatus = await _context.HistoStatus.FindAsync(id);
            if (existingHistoStatus == null)
                return null;

            existingHistoStatus.DateChangement = histoStatus.DateChangement;
            existingHistoStatus.IdCompte = histoStatus.IdCompte;
            existingHistoStatus.IdStatus = histoStatus.IdStatus;

            await _context.SaveChangesAsync();
            return existingHistoStatus;
        }

        public async Task<bool> DeleteHistoStatusAsync(int id)
        {
            var histoStatus = await _context.HistoStatus.FindAsync(id);
            if (histoStatus == null)
                return false;

            _context.HistoStatus.Remove(histoStatus);
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<IEnumerable<HistoStatus>> GetHistoStatusByCompteIdAsync(int compteId)
        {
            return await _context.HistoStatus
                .Include(h => h.Status)
                .Where(h => h.IdCompte == compteId)
                .OrderByDescending(h => h.DateChangement)
                .ToListAsync();
        }
    }
}