using Microsoft.AspNetCore.Mvc;
using depot.Models;
using depot.Services;

namespace depot.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class TypeTransactionsController : ControllerBase
    {
        private readonly ITypeTransactionService _typeTransactionService;

        public TypeTransactionsController(ITypeTransactionService typeTransactionService)
        {
            _typeTransactionService = typeTransactionService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<TypeTransaction>>> GetTypeTransactions()
        {
            var types = await _typeTransactionService.GetAllTypeTransactionsAsync();
            return Ok(types);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<TypeTransaction>> GetTypeTransaction(int id)
        {
            var type = await _typeTransactionService.GetTypeTransactionByIdAsync(id);
            if (type == null)
                return NotFound();
            
            return Ok(type);
        }

        [HttpPost]
        public async Task<ActionResult<TypeTransaction>> PostTypeTransaction(TypeTransaction typeTransaction)
        {
            var createdType = await _typeTransactionService.CreateTypeTransactionAsync(typeTransaction);
            return CreatedAtAction(nameof(GetTypeTransaction), new { id = createdType.Id }, createdType);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutTypeTransaction(int id, TypeTransaction typeTransaction)
        {
            if (id != typeTransaction.Id)
                return BadRequest();
            
            var updatedType = await _typeTransactionService.UpdateTypeTransactionAsync(id, typeTransaction);
            if (updatedType == null)
                return NotFound();
            
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTypeTransaction(int id)
        {
            var result = await _typeTransactionService.DeleteTypeTransactionAsync(id);
            if (!result)
                return NotFound();
            
            return NoContent();
        }
    }
}