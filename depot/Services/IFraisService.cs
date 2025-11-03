using depot.Models;

namespace depot.Services
{
    public interface IFraisService
    {
        Task<IEnumerable<Frais>> GetAllFraisAsync();
        Task<Frais?> GetFraisByIdAsync(int id);
        Task<Frais> CreateFraisAsync(Frais frais);
        Task<Frais?> UpdateFraisAsync(int id, Frais frais);
        Task<bool> DeleteFraisAsync(int id);
        Task<IEnumerable<Frais>> GetFraisByCompteIdAsync(int compteId);
    }
}