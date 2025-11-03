using Microsoft.AspNetCore.Mvc;
using depot.Models;
using depot.Services;

namespace depot.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class StatusController : ControllerBase
    {
        private readonly IStatusService _statusService;

        public StatusController(IStatusService statusService)
        {
            _statusService = statusService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Status>>> GetStatus()
        {
            var status = await _statusService.GetAllStatusAsync();
            return Ok(status);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Status>> GetStatus(int id)
        {
            var status = await _statusService.GetStatusByIdAsync(id);
            if (status == null)
                return NotFound();
            
            return Ok(status);
        }

        [HttpPost]
        public async Task<ActionResult<Status>> PostStatus(Status status)
        {
            var createdStatus = await _statusService.CreateStatusAsync(status);
            return CreatedAtAction(nameof(GetStatus), new { id = createdStatus.Id }, createdStatus);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutStatus(int id, Status status)
        {
            if (id != status.Id)
                return BadRequest();
            
            var updatedStatus = await _statusService.UpdateStatusAsync(id, status);
            if (updatedStatus == null)
                return NotFound();
            
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteStatus(int id)
        {
            var result = await _statusService.DeleteStatusAsync(id);
            if (!result)
                return NotFound();
            
            return NoContent();
        }
    }
}