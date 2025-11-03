using Microsoft.AspNetCore.Mvc;
using depot.Models;
using depot.Services;

namespace depot.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ComptesController : ControllerBase
    {
        private readonly ICompteService _compteService;

        public ComptesController(ICompteService compteService)
        {
            _compteService = compteService;
        }

        [HttpGet]   
        public async Task<ActionResult<IEnumerable<Compte>>> GetComptes()
        {
            var comptes = await _compteService.GetAllComptesAsync();
            return Ok(comptes);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Compte>> GetCompte(int id)
        {
            var compte = await _compteService.GetCompteByIdAsync(id);
            if (compte == null)
                return NotFound();
            
            return Ok(compte);
        }

        [HttpPost]
        public async Task<ActionResult<Compte>> PostCompte(Compte compte)
        {
            var createdCompte = await _compteService.CreateCompteAsync(compte);
            return CreatedAtAction(nameof(GetCompte), new { id = createdCompte.Id }, createdCompte);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutCompte(int id, Compte compte)
        {
            if (id != compte.Id)
                return BadRequest();
            
            var updatedCompte = await _compteService.UpdateCompteAsync(id, compte);
            if (updatedCompte == null)
                return NotFound();
            
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCompte(int id)
        {
            var result = await _compteService.DeleteCompteAsync(id);
            if (!result)
                return NotFound();
            
            return NoContent();
        }
    }
}