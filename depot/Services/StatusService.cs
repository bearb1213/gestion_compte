using depot.Data;
using depot.Models;
using Microsoft.EntityFrameworkCore;

namespace depot.Services
{
    public class StatusService : IStatusService
    {
        private readonly AppDbContext _context;

        public StatusService(AppDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Status>> GetAllStatusAsync()
        {
            return await _context.Status.ToListAsync();
        }

        public async Task<Status?> GetStatusByIdAsync(int id)
        {
            return await _context.Status.FindAsync(id);
        }

        public async Task<Status> CreateStatusAsync(Status status)
        {
            _context.Status.Add(status);
            await _context.SaveChangesAsync();
            return status;
        }

        public async Task<Status?> UpdateStatusAsync(int id, Status status)
        {
            var existingStatus = await _context.Status.FindAsync(id);
            if (existingStatus == null)
                return null;

            existingStatus.Libelle = status.Libelle;
            await _context.SaveChangesAsync();
            return existingStatus;
        }

        public async Task<bool> DeleteStatusAsync(int id)
        {
            var status = await _context.Status.FindAsync(id);
            if (status == null)
                return false;

            _context.Status.Remove(status);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}