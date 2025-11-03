using depot.Models;

namespace depot.Services
{
    public interface IFixeService
    {
        Task<IEnumerable<Fixe>> GetAllFixesAsync();
        Task<Fixe?> GetFixeByIdAsync(int id);
        Task<Fixe> CreateFixeAsync(Fixe fixe);
        Task<Fixe?> UpdateFixeAsync(int id, Fixe fixe);
        Task<bool> DeleteFixeAsync(int id);
    }
}