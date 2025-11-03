using Microsoft.AspNetCore.Mvc;
using depot.Models;
using depot.Services;

namespace depot.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class TransactionsController : ControllerBase
    {
        private readonly ITransactionService _transactionService;

        public TransactionsController(ITransactionService transactionService)
        {
            _transactionService = transactionService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Transaction>>> GetTransactions()
        {
            var transactions = await _transactionService.GetAllTransactionsAsync();
            return Ok(transactions);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Transaction>> GetTransaction(int id)
        {
            var transaction = await _transactionService.GetTransactionByIdAsync(id);
            if (transaction == null)
                return NotFound();
            
            return Ok(transaction);
        }

        [HttpGet("compte/{compteId}")]
        public async Task<ActionResult<IEnumerable<Transaction>>> GetTransactionsByCompte(int compteId)
        {
            var transactions = await _transactionService.GetTransactionsByCompteIdAsync(compteId);
            return Ok(transactions);
        }

        [HttpPost]
        public async Task<ActionResult<Transaction>> PostTransaction(Transaction transaction)
        {
            var createdTransaction = await _transactionService.CreateTransactionAsync(transaction);
            return CreatedAtAction(nameof(GetTransaction), new { id = createdTransaction.Id }, createdTransaction);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutTransaction(int id, Transaction transaction)
        {
            if (id != transaction.Id)
                return BadRequest();
            
            var updatedTransaction = await _transactionService.UpdateTransactionAsync(id, transaction);
            if (updatedTransaction == null)
                return NotFound();
            
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTransaction(int id)
        {
            var result = await _transactionService.DeleteTransactionAsync(id);
            if (!result)
                return NotFound();
            
            return NoContent();
        }
    }
}