using depot.Data;
using depot.Models;
using Microsoft.EntityFrameworkCore;

namespace depot.Services
{
    public class TransactionService : ITransactionService
    {
        private readonly AppDbContext _context;

        public TransactionService(AppDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Transaction>> GetAllTransactionsAsync()
        {
            return await _context.Transactions
                .Include(t => t.TypeTransaction)
                .Include(t => t.Compte)
                .ToListAsync();
        }

        public async Task<Transaction?> GetTransactionByIdAsync(int id)
        {
            return await _context.Transactions
                .Include(t => t.TypeTransaction)
                .Include(t => t.Compte)
                .FirstOrDefaultAsync(t => t.Id == id);
        }

        public async Task<Transaction> CreateTransactionAsync(Transaction transaction)
        {
            _context.Transactions.Add(transaction);
            await _context.SaveChangesAsync();
            return transaction;
        }

        public async Task<Transaction?> UpdateTransactionAsync(int id, Transaction transaction)
        {
            var existingTransaction = await _context.Transactions.FindAsync(id);
            if (existingTransaction == null)
                return null;

            existingTransaction.Montant = transaction.Montant;
            existingTransaction.DateTransaction = transaction.DateTransaction;
            existingTransaction.Description = transaction.Description;
            existingTransaction.Mouvement = transaction.Mouvement;
            existingTransaction.IdType = transaction.IdType;
            existingTransaction.IdCompte = transaction.IdCompte;

            await _context.SaveChangesAsync();
            return existingTransaction;
        }

        public async Task<bool> DeleteTransactionAsync(int id)
        {
            var transaction = await _context.Transactions.FindAsync(id);
            if (transaction == null)
                return false;

            _context.Transactions.Remove(transaction);
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<IEnumerable<Transaction>> GetTransactionsByCompteIdAsync(int compteId)
        {
            return await _context.Transactions
                .Include(t => t.TypeTransaction)
                .Where(t => t.IdCompte == compteId)
                .ToListAsync();
        }
    }
}