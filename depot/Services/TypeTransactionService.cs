using depot.Data;
using depot.Models;
using Microsoft.EntityFrameworkCore;

namespace depot.Services
{
    public class TypeTransactionService : ITypeTransactionService
    {
        private readonly AppDbContext _context;

        public TypeTransactionService(AppDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<TypeTransaction>> GetAllTypeTransactionsAsync()
        {
            return await _context.TypeTransactions.ToListAsync();
        }

        public async Task<TypeTransaction?> GetTypeTransactionByIdAsync(int id)
        {
            return await _context.TypeTransactions.FindAsync(id);
        }

        public async Task<TypeTransaction> CreateTypeTransactionAsync(TypeTransaction typeTransaction)
        {
            _context.TypeTransactions.Add(typeTransaction);
            await _context.SaveChangesAsync();
            return typeTransaction;
        }

        public async Task<TypeTransaction?> UpdateTypeTransactionAsync(int id, TypeTransaction typeTransaction)
        {
            var existingType = await _context.TypeTransactions.FindAsync(id);
            if (existingType == null)
                return null;

            existingType.Libelle = typeTransaction.Libelle;
            await _context.SaveChangesAsync();
            return existingType;
        }

        public async Task<bool> DeleteTypeTransactionAsync(int id)
        {
            var typeTransaction = await _context.TypeTransactions.FindAsync(id);
            if (typeTransaction == null)
                return false;

            _context.TypeTransactions.Remove(typeTransaction);
            await _context.SaveChangesAsync();
            return true;
        }
    }
}