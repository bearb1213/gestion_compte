using Microsoft.AspNetCore.Mvc;
using depot.Models;
using depot.Services;

namespace depot.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class FixesController : ControllerBase
    {
        private readonly IFixeService _fixeService;

        public FixesController(IFixeService fixeService)
        {
            _fixeService = fixeService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Fixe>>> GetFixes()
        {
            var fixes = await _fixeService.GetAllFixesAsync();
            return Ok(fixes);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Fixe>> GetFixe(int id)
        {
            var fixe = await _fixeService.GetFixeByIdAsync(id);
            if (fixe == null)
                return NotFound();
            
            return Ok(fixe);
        }

        [HttpPost]
        public async Task<ActionResult<Fixe>> PostFixe(Fixe fixe)
        {
            var createdFixe = await _fixeService.CreateFixeAsync(fixe);
            return CreatedAtAction(nameof(GetFixe), new { id = createdFixe.Id }, createdFixe);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutFixe(int id, Fixe fixe)
        {
            if (id != fixe.Id)
                return BadRequest();
            
            var updatedFixe = await _fixeService.UpdateFixeAsync(id, fixe);
            if (updatedFixe == null)
                return NotFound();
            
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteFixe(int id)
        {
            var result = await _fixeService.DeleteFixeAsync(id);
            if (!result)
                return NotFound();
            
            return NoContent();
        }
    }
}