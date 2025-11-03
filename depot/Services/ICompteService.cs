using depot.Models;

namespace depot.Services
{
    public interface ICompteService
    {
        Task<IEnumerable<Compte>> GetAllComptesAsync();
        Task<Compte?> GetCompteByIdAsync(int id);
        Task<Compte> CreateCompteAsync(Compte compte);
        Task<Compte?> UpdateCompteAsync(int id, Compte compte);
        Task<bool> DeleteCompteAsync(int id);
    }
}