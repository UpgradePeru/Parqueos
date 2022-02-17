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
    [Route("distritos")]
    public class DistritoController : ControllerBase
    {
        private readonly IDistritoService _distritoService;

        public DistritoController(IDistritoService DistritoService)
        {
            _distritoService = DistritoService;
        }

        [HttpGet]
        public async Task<ActionResult<DataCollection<DistritoDto>>> GetAll(int page = 1, int take = 50)
        {
            return await _distritoService.GetAll(page, take);
        }

        // Ex: Distritos/1
        [HttpGet("{id}")]
        public async Task<ActionResult<DistritoDto>> GetById(int id)
        {
            return await _distritoService.GetById(id);
        }

        [HttpPost]
        public async Task<ActionResult> Create(DistritoCreateDto model)
        {
            var result = await _distritoService.Create(model);

            return CreatedAtAction(
                "GetById",
                new { id = result.Id },
                result
            );
        }

        [HttpPut("{id}")]
        public async Task<ActionResult> Update(int id, DistritoUpdateDto model)
        {
            await _distritoService.Update(id, model);
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
            await _distritoService.Remove(id);
            //return NoContent();
            return Ok(new
            {
                code = 1,
                status = "Ok",
                msg = "Registro eliminado."
            });
        }

        [HttpGet("disponibles")]
        public async Task<ActionResult<DataCollection<DistritoDto>>> GetAvailables(int page = 1, int take = 50)
        {
            return await _distritoService.GetAvailables(page, take);
        }
        //extras

        [HttpGet("nx")]
        public async Task<ActionResult> AddDistritos()
        {
            DistritoCreateDto d1 = new DistritoCreateDto();
            d1.Name = "Jesus Maria";
            d1.Description = "Jesus Maria";
            var result = await _distritoService.Create(d1);

            DistritoCreateDto d2 = new DistritoCreateDto();
            d2.Name = "Pueblo Libre";
            d2.Description = "Pueblo Libre";
            result = await _distritoService.Create(d2);

            DistritoCreateDto d3 = new DistritoCreateDto();
            d3.Name = "San Isidro";
            d3.Description = "San Isidro";
            result = await _distritoService.Create(d3);

            DistritoCreateDto d4 = new DistritoCreateDto();
            d4.Name = "Miraflores";
            d4.Description = "Miraflores";
            result = await _distritoService.Create(d4);

            return Ok();
        }
    }
}
