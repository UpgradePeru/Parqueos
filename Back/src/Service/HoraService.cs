using AutoMapper;
using Microsoft.EntityFrameworkCore;
using Model;
using Model.DTOs;
using Persistence.Database;
using Service.Commons;
using Service.Extensions;
using System.Linq;
using System.Threading.Tasks;

namespace Service
{
    public interface IHoraService
    {
        Task<DataCollection<HoraDto>> GetAll(int page, int take);
        Task<HoraDto> GetById(int id);
        Task<HoraDto> Create(HoraCreateDto model);
        Task Update(int id, HoraUpdateDto model);
        Task Remove(int id);

        Task<DataCollection<HoraDto>> GetDisponibles(int canchaId, string fecha, int page, int take);
    }

    public class HoraService : IHoraService
    {
        private readonly ApplicationDbContext _context;
        private readonly IMapper _mapper;

        public HoraService(
            ApplicationDbContext context,
            IMapper mapper
        )
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task<DataCollection<HoraDto>> GetAll(int page, int take)
        {
            return _mapper.Map<DataCollection<HoraDto>>(
                await _context.Horas.OrderByDescending(x => x.Id)
                              .AsQueryable()
                              .PagedAsync(page, take)
            );
        }

        public async Task<HoraDto> GetById(int id)
        {
            return _mapper.Map<HoraDto>(
                await _context.Horas.SingleAsync(x => x.Id == id)
            );
        }

        public async Task<HoraDto> Create(HoraCreateDto model)
        {
            var entry = new Hora
            {
                Name = model.Name,
                Order = model.Order,
                Enable = model.Enable
            };

            await _context.AddAsync(entry);
            await _context.SaveChangesAsync();

            return _mapper.Map<HoraDto>(entry);
        }

        public async Task Update(int id, HoraUpdateDto model)
        {
            var entry = await _context.Horas.SingleAsync(x => x.Id == id);

            entry.Name = model.Name;
            entry.Order = model.Order;
            entry.Enable = model.Enable;

            await _context.SaveChangesAsync();
        }

        public async Task Remove(int id)
        {
            _context.Remove(new Hora
            {
                Id = id
            });

            await _context.SaveChangesAsync();
        }

        public async Task<DataCollection<HoraDto>> GetDisponibles(int canchaId, string fecha, int page, int take)
        {
            return _mapper.Map<DataCollection<HoraDto>>(
                await _context.Horas.OrderBy(x => x.Order)
                              .AsQueryable()
                              .PagedAsync(page, take)
            );
        }
    }
}
