using depot.Models;

namespace depot.Services
{
    public interface ITypeTransactionService
    {
        Task<IEnumerable<TypeTransaction>> GetAllTypeTransactionsAsync();
        Task<TypeTransaction?> GetTypeTransactionByIdAsync(int id);
        Task<TypeTransaction> CreateTypeTransactionAsync(TypeTransaction typeTransaction);
        Task<TypeTransaction?> UpdateTypeTransactionAsync(int id, TypeTransaction typeTransaction);
        Task<bool> DeleteTypeTransactionAsync(int id);
    }
}