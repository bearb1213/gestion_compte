using Microsoft.AspNetCore.Mvc;
using depot.Models;
using depot.Services;

namespace depot.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class FraisController : ControllerBase
    {
        private readonly IFraisService _fraisService;

        public FraisController(IFraisService fraisService)
        {
            _fraisService = fraisService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Frais>>> GetFrais()
        {
            var frais = await _fraisService.GetAllFraisAsync();
            return Ok(frais);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Frais>> GetFrais(int id)
        {
            var frais = await _fraisService.GetFraisByIdAsync(id);
            if (frais == null)
                return NotFound();
            
            return Ok(frais);
        }

        [HttpGet("compte/{compteId}")]
        public async Task<ActionResult<IEnumerable<Frais>>> GetFraisByCompte(int compteId)
        {
            var frais = await _fraisService.GetFraisByCompteIdAsync(compteId);
            return Ok(frais);
        }

        [HttpPost]
        public async Task<ActionResult<Frais>> PostFrais(Frais frais)
        {
            var createdFrais = await _fraisService.CreateFraisAsync(frais);
            return CreatedAtAction(nameof(GetFrais), new { id = createdFrais.Id }, createdFrais);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutFrais(int id, Frais frais)
        {
            if (id != frais.Id)
                return BadRequest();
            
            var updatedFrais = await _fraisService.UpdateFraisAsync(id, frais);
            if (updatedFrais == null)
                return NotFound();
            
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteFrais(int id)
        {
            var result = await _fraisService.DeleteFraisAsync(id);
            if (!result)
                return NotFound();
            
            return NoContent();
        }
    }
}