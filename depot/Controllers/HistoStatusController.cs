using Microsoft.AspNetCore.Mvc;
using depot.Models;
using depot.Services;

namespace depot.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class HistoStatusController : ControllerBase
    {
        private readonly IHistoStatusService _histoStatusService;

        public HistoStatusController(IHistoStatusService histoStatusService)
        {
            _histoStatusService = histoStatusService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<HistoStatus>>> GetHistoStatus()
        {
            var histoStatus = await _histoStatusService.GetAllHistoStatusAsync();
            return Ok(histoStatus);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<HistoStatus>> GetHistoStatus(int id)
        {
            var histoStatus = await _histoStatusService.GetHistoStatusByIdAsync(id);
            if (histoStatus == null)
                return NotFound();
            
            return Ok(histoStatus);
        }

        [HttpGet("compte/{compteId}")]
        public async Task<ActionResult<IEnumerable<HistoStatus>>> GetHistoStatusByCompte(int compteId)
        {
            var histoStatus = await _histoStatusService.GetHistoStatusByCompteIdAsync(compteId);
            return Ok(histoStatus);
        }

        [HttpPost]
        public async Task<ActionResult<HistoStatus>> PostHistoStatus(HistoStatus histoStatus)
        {
            var createdHistoStatus = await _histoStatusService.CreateHistoStatusAsync(histoStatus);
            return CreatedAtAction(nameof(GetHistoStatus), new { id = createdHistoStatus.Id }, createdHistoStatus);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutHistoStatus(int id, HistoStatus histoStatus)
        {
            if (id != histoStatus.Id)
                return BadRequest();
            
            var updatedHistoStatus = await _histoStatusService.UpdateHistoStatusAsync(id, histoStatus);
            if (updatedHistoStatus == null)
                return NotFound();
            
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteHistoStatus(int id)
        {
            var result = await _histoStatusService.DeleteHistoStatusAsync(id);
            if (!result)
                return NotFound();
            
            return NoContent();
        }
    }
}