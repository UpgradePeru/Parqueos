using Core.Api.Commons;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Model.DTOs;
using Service;
using Service.Commons;
using System.Threading.Tasks;

namespace Core.Api.Controllers
{
    [Authorize]
    [ApiController]
    [Route("parqueos")]
    public class ParqueoController : ControllerBase
    {
        private readonly IParqueoService _parqueoService;

        public ParqueoController(IParqueoService ParqueoService)
        {
            _parqueoService = ParqueoService;
        }

        [HttpGet]
        public async Task<ActionResult<DataCollection<ParqueoDto>>> GetAll(int page = 1, int take = 20)
        {
            return await _parqueoService.GetAll(page, take);
        }

        // Ex: Parqueos/1
        [HttpGet("{id}")]
        public async Task<ActionResult<ParqueoDto>> GetById(int id)
        {
            return await _parqueoService.GetById(id);
        }

        [HttpPost]
        public async Task<ActionResult> Create(ParqueoCreateDto model)
        {
            var result = await _parqueoService.Create(model);

            return CreatedAtAction(
                "GetById",
                new { id = result.Id },
                result
            );
        }

        [HttpPut("{id}")]
        public async Task<ActionResult> Update(int id, ParqueoUpdateDto model)
        {
            await _parqueoService.Update(id, model);
            //return NoContent();
            return Ok(new
            {
                code = 1,
                status = "Ok",
                msg = "Registro actualizado."
            });
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> Remove(int id)
        {
            await _parqueoService.Remove(id);
            //return NoContent();
            return Ok(new
            {
                code = 1,
                status = "Ok",
                msg = "Registro eliminado."
            });
        }


        [HttpGet("bydistrito")]
        public async Task<ActionResult<DataCollection<ParqueoDto>>> GetAllDistrito(int distritoId, int page = 1, int take = 20)
        {
            return await _parqueoService.GetAllDistrito(distritoId, page, take);
        }
    }
}
