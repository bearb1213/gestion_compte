using depot.Models;

namespace depot.Services
{
    public interface ITransactionService
    {
        Task<IEnumerable<Transaction>> GetAllTransactionsAsync();
        Task<Transaction?> GetTransactionByIdAsync(int id);
        Task<Transaction> CreateTransactionAsync(Transaction transaction);
        Task<Transaction?> UpdateTransactionAsync(int id, Transaction transaction);
        Task<bool> DeleteTransactionAsync(int id);
        Task<IEnumerable<Transaction>> GetTransactionsByCompteIdAsync(int compteId);
    }
}