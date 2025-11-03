using depot.Models;

namespace depot.Services
{
    public interface IHistoStatusService
    {
        Task<IEnumerable<HistoStatus>> GetAllHistoStatusAsync();
        Task<HistoStatus?> GetHistoStatusByIdAsync(int id);
        Task<HistoStatus> CreateHistoStatusAsync(HistoStatus histoStatus);
        Task<HistoStatus?> UpdateHistoStatusAsync(int id, HistoStatus histoStatus);
        Task<bool> DeleteHistoStatusAsync(int id);
        Task<IEnumerable<HistoStatus>> GetHistoStatusByCompteIdAsync(int compteId);
    }
}