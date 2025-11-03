using depot.Models;

namespace depot.Services
{
    public interface IStatusService
    {
        Task<IEnumerable<Status>> GetAllStatusAsync();
        Task<Status?> GetStatusByIdAsync(int id);
        Task<Status> CreateStatusAsync(Status status);
        Task<Status?> UpdateStatusAsync(int id, Status status);
        Task<bool> DeleteStatusAsync(int id);
    }
}